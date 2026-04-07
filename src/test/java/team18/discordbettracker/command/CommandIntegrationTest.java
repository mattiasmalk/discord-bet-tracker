package team18.discordbettracker.command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import team18.discordbettracker.formatter.BetHistoryFormatter;
import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.BetStatus;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.repository.BetRepository;
import team18.discordbettracker.repository.UserRepository;
import team18.discordbettracker.service.BetDtoMapper;
import team18.discordbettracker.service.BetService;
import team18.discordbettracker.service.UserService;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@DataJpaTest
@ContextConfiguration(classes = CommandIntegrationTest.TestApplication.class)
@Import({
        AddBetCommand.class,
        HistoryCommand.class,
        SettleBetCommand.class,
        BetService.class,
        BetDtoMapper.class,
        UserService.class,
        BetHistoryFormatter.class
})
class CommandIntegrationTest {

    @Autowired
    private AddBetCommand addBetCommand;

    @Autowired
    private HistoryCommand historyCommand;

    @Autowired
    private SettleBetCommand settleBetCommand;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetService betService;

    @Autowired
    private BetHistoryFormatter betHistoryFormatter;

    @BeforeEach
    void cleanDatabase() {
        betRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addBetCommand_persistsBetAndRepliesWithCorrectMessage() {
        var event = mock(SlashCommandInteractionEvent.class);
        var discordUser = mock(User.class);
        var guild = mock(Guild.class);
        var descriptionOption = mock(OptionMapping.class);
        var stakeOption = mock(OptionMapping.class);
        var oddsOption = mock(OptionMapping.class);
        var replyAction = mock(ReplyCallbackAction.class);

        when(event.getUser()).thenReturn(discordUser);
        when(event.getGuild()).thenReturn(guild);
        when(guild.getIdLong()).thenReturn(12345L);
        when(discordUser.getIdLong()).thenReturn(777L);
        when(discordUser.getName()).thenReturn("alice");

        when(event.getOption(AddBetCommand.ADD_BET_OPTION_DESCRIPTION)).thenReturn(descriptionOption);
        when(event.getOption(AddBetCommand.ADD_BET_OPTION_STAKE)).thenReturn(stakeOption);
        when(event.getOption(AddBetCommand.ADD_BET_OPTION_ODDS)).thenReturn(oddsOption);

        when(descriptionOption.getAsString()).thenReturn("Arsenal vs Chelsea");
        when(stakeOption.getAsDouble()).thenReturn(15.5);
        when(oddsOption.getAsDouble()).thenReturn(2.35);

        when(event.reply(anyString())).thenReturn(replyAction);

        addBetCommand.execute(event);

        verify(event).reply(eq("User alice has just placed a bet \"Arsenal vs Chelsea\" for 15.50 with 2.350 odds"));
        verify(replyAction).queue();

        var userId = new UserId(777L, 12345L);
        var savedUser = userRepository.findById(userId);
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getName()).isEqualTo("alice");

        var savedBets = betRepository.findAll();
        assertThat(savedBets).hasSize(1);

        var savedBet = savedBets.getFirst();
        assertThat(savedBet.getDescription()).isEqualTo("Arsenal vs Chelsea");
        assertThat(savedBet.getStake()).isEqualByComparingTo(new BigDecimal("15.5"));
        assertThat(savedBet.getOdds()).isEqualByComparingTo(new BigDecimal("2.35"));
        assertThat(savedBet.getStatus()).isEqualTo(BetStatus.OPEN);
        assertThat(savedBet.getUser().getUserId()).isEqualTo(userId);
        assertThat(savedBet.getCreatedAt()).isNotNull();
    }

    @Test
    void historyCommand_repliesWithFormattedMessageAndUsesPersistedValues() {
        var userId = new UserId(901L, 2468L);
        var user = userRepository.save(team18.discordbettracker.model.User.builder()
                .userId(userId)
                .name("bob")
                .createdAt(Instant.parse("2026-04-05T10:00:00Z"))
                .build());

        betRepository.save(Bet.builder()
                .user(user)
                .description("First long bet description that gets clipped")
                .stake(new BigDecimal("10.000"))
                .odds(new BigDecimal("1.900"))
                .status(BetStatus.OPEN)
                .createdAt(Instant.parse("2026-04-05T10:15:00Z"))
                .build());

        betRepository.save(Bet.builder()
                .user(user)
                .description("Second bet")
                .stake(new BigDecimal("20.000"))
                .odds(new BigDecimal("2.500"))
                .status(BetStatus.WON)
                .createdAt(Instant.parse("2026-04-05T11:15:00Z"))
                .resolvedAt(Instant.parse("2026-04-05T12:00:00Z"))
                .build());

        var event = mock(SlashCommandInteractionEvent.class);
        var discordUser = mock(User.class);
        var guild = mock(Guild.class);
        var replyAction = mock(ReplyCallbackAction.class);

        when(event.getUser()).thenReturn(discordUser);
        when(event.getGuild()).thenReturn(guild);
        when(discordUser.getIdLong()).thenReturn(901L);
        when(guild.getIdLong()).thenReturn(2468L);
        when(event.reply(anyString())).thenReturn(replyAction);

        historyCommand.execute(event);

        var persistedBets = betRepository.findLatestByUserId(userId);
        assertThat(persistedBets).hasSize(2);
        assertThat(persistedBets)
                .extracting(Bet::getDescription)
                .containsExactly("Second bet", "First long bet description that gets clipped");

        var expectedMessage = betHistoryFormatter.format(betService.getHistory(userId));
        verify(event).reply(eq(expectedMessage));
        verify(replyAction).queue();
    }

    @Test
    void settleBetCommand_setsBetStatusAndResolvedAt() {
        var userId = new UserId(333L, 444L);
        var user = userRepository.save(team18.discordbettracker.model.User.builder()
                .userId(userId)
                .name("charlie")
                .createdAt(Instant.parse("2026-04-05T10:00:00Z"))
                .build());

        var bet = betRepository.save(Bet.builder()
                .user(user)
                .description("Test bet")
                .stake(new BigDecimal("10.00"))
                .odds(new BigDecimal("1.50"))
                .status(BetStatus.OPEN)
                .createdAt(Instant.parse("2026-04-05T10:15:00Z"))
                .build());

        var event = mock(SlashCommandInteractionEvent.class);
        var discordUser = mock(User.class);
        var guild = mock(Guild.class);
        var betIdOption = mock(OptionMapping.class);
        var resultOption = mock(OptionMapping.class);
        var replyAction = mock(ReplyCallbackAction.class);

        when(event.getUser()).thenReturn(discordUser);
        when(event.getGuild()).thenReturn(guild);
        when(discordUser.getIdLong()).thenReturn(333L);
        when(guild.getIdLong()).thenReturn(444L);

        when(event.getOption(SettleBetCommand.SETTLE_BET_OPTION_BET_ID)).thenReturn(betIdOption);
        when(event.getOption(SettleBetCommand.SETTLE_BET_OPTION_RESULT)).thenReturn(resultOption);
        when(betIdOption.getAsLong()).thenReturn(bet.getId());
        when(resultOption.getAsString()).thenReturn("WON");
        when(event.reply(anyString())).thenReturn(replyAction);

        settleBetCommand.execute(event);

        verify(event).reply(anyString());
        verify(replyAction).queue();

        var savedBet = betRepository.findById(bet.getId()).orElseThrow();
        assertThat(savedBet.getStatus()).isEqualTo(BetStatus.WON);
        assertThat(savedBet.getResolvedAt()).isNotNull();
    }

    @SpringBootConfiguration
    @EntityScan("team18.discordbettracker.model")
    @EnableJpaRepositories("team18.discordbettracker.repository")
    @SpringBootApplication(scanBasePackages = "team18.discordbettracker")
    static class TestApplication {
    }
}
