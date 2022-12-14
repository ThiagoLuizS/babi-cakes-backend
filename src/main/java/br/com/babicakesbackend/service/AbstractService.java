package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.exception.NotFoundException;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.util.ConstantsMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractService<T, View, Form> {

    protected abstract JpaRepository<T, Long> getRepository();

    protected abstract MapStructMapper<T, View, Form> getConverter();

    public Optional<View> findViewById(Long id) {
        log.info(">> findById [id={}]", id);
        Optional<T> view = getRepository().findById(id);
        log.info("<< findById [view={}]", view);
        return view.map(getConverter()::entityToView);
    }

    public Optional<T> findEntityById(Long id) {
        log.info(">> findById [id={}]", id);
        Optional<T> entity = getRepository().findById(id);
        log.info("<< findById [view isPresent={}]", entity.isPresent());
        return entity;
    }

    public Page<View> findByPage(Pageable pageable) {
        Page<T> t = getRepository().findAll(pageable);
        List<View> view = t.getContent().stream().map(getConverter()::entityToView).collect(Collectors.toList());
        return new PageImpl<>(view);
    }

    public List<View> findAll() {
        List<T> views = getRepository().findAll();
        log.info("findAll [views={}]", views.stream().count());
        return views.stream().map(getConverter()::entityToView).collect(Collectors.toList());
    }

    public View save(Form form) {
        try {
            log.debug(">> save [form={}] ", form);
            T converting = getConverter().formToEntity(form);
            log.debug(">> save [converting={}] ", converting);
            T t = getRepository().save(converting);
            log.debug("<< save [t={}] ", t);
            View view =  getConverter().entityToView(t);
            log.debug("<< save [view={}] ", view);
            return view;
        } catch (Exception e) {
            log.error("<< save [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public T saveToEntity(Form form) {
        try {
            log.debug(">> save [form={}] ", form);
            T converting = getConverter().formToEntity(form);
            log.debug(">> save [converting={}] ", converting);
            T entity = getRepository().save(converting);
            log.debug(">> save [entity={}] ", entity);
            return entity;
        } catch (Exception e) {
            log.error("<< save [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public View update(Form form) {
        log.debug(">> update [form={}] ", form);
        return save(form);
    }

    public void delete(Long id) {
        try {
            log.debug(">> delete [id={}] ", id);
            Optional<T> t = getRepository().findById(id);
            if(!t.isPresent()) {
                throw new GlobalException(ConstantsMessageUtils.NOT_FOUND);
            }
            getRepository().delete(t.get());
        } catch (DataIntegrityViolationException e) {
            log.error("<< delete [error={}]", e.getMessage());
            throw new GlobalException("Não é possivel excluir, pois está sendo utilizado.");
        } catch (Exception e) {
            log.error("<< delete [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }

    }
}
