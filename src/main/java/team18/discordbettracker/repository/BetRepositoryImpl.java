package team18.discordbettracker.repository;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import team18.discordbettracker.model.Bet;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@NullMarked
public class BetRepositoryImpl implements BetRepository{
	@Override
	public void flush() {

	}

	@Override
	public <S extends Bet> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	public <S extends Bet> List<S> saveAllAndFlush(Iterable<S> entities) {
		return List.of();
	}

	@Override
	public void deleteAllInBatch(Iterable<Bet> entities) {

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> longs) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public Bet getOne(Long aLong) {
		return null;
	}

	@Override
	public Bet getById(Long aLong) {
		return null;
	}

	@Override
	public Bet getReferenceById(Long aLong) {
		return null;
	}

	@Override
	public <S extends Bet> List<S> findAll(Example<S> example) {
		return List.of();
	}

	@Override
	public <S extends Bet> List<S> findAll(Example<S> example, Sort sort) {
		return List.of();
	}

	@Override
	public <S extends Bet> List<S> saveAll(Iterable<S> entities) {
		return List.of();
	}

	@Override
	public List<Bet> findAll() {
		return List.of();
	}

	@Override
	public List<Bet> findAllById(Iterable<Long> longs) {
		return List.of();
	}

	@Override
	public <S extends Bet> S save(S entity) {
		return null;
	}

	@Override
	public Optional<Bet> findById(Long aLong) {
		return Optional.empty();
	}

	@Override
	public boolean existsById(Long aLong) {
		return false;
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public void deleteById(Long aLong) {

	}

	@Override
	public void delete(Bet entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> longs) {

	}

	@Override
	public void deleteAll(Iterable<? extends Bet> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public List<Bet> findAll(Sort sort) {
		return List.of();
	}

	@Override
	public Page<Bet> findAll(Pageable pageable) {
		return null;
	}

	@Override
	public <S extends Bet> Optional<S> findOne(Example<S> example) {
		return Optional.empty();
	}

	@Override
	public <S extends Bet> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	public <S extends Bet> long count(Example<S> example) {
		return 0;
	}

	@Override
	public <S extends Bet> boolean exists(Example<S> example) {
		return false;
	}

	@Override
	public <S extends Bet, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return null;
	}
}
