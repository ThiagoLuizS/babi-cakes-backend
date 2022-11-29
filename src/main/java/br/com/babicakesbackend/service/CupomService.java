package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.CupomForm;
import br.com.babicakesbackend.models.dto.CupomView;
import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
import br.com.babicakesbackend.models.mapper.CupomMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.UserMapperImpl;
import br.com.babicakesbackend.repository.CupomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CupomService extends AbstractService<Cupom, CupomView, CupomForm> {

    private final CupomRepository repository;
    private final CupomMapperImpl cupomMapper;
    private final UserService userService;
    private final UserMapperImpl userMapper;
    private final AuthenticationService authenticationService;

    public void saveCustom(CupomForm cupomForm) {
        try {
            boolean existCupom = repository.existsByCode(cupomForm.getCode());
            if(existCupom) {
                throw new GlobalException("Um cupom com este código já existe");
            }
            if(cupomForm.isCupomIsValueMin() && cupomForm.getCupomValueMin().compareTo(BigDecimal.ZERO) <= 0) {
                throw new GlobalException("Se definir que o cupom tenha um valor mínimo, o valor deverá ser maior do que R$ 0,00");
            }
            log.info(">> saveCustom [cupomForm={}]", cupomForm);
            Optional<User> user = userService.findEntityById(cupomForm.getUser().getId());
            if(!user.isPresent()) {
                throw new GlobalException("Usuário não existe / não está ativo");
            }
            cupomForm.setUser(userMapper.entityToForm(user.get()));
            cupomForm.setDateCreated(new Date());
            save(cupomForm);
            log.info("<< saveCustom [id={}]", cupomForm.getId());
        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public Optional<Cupom> findByCode(String code) {
        log.info(">> findByCode [code={}]", code);
        Optional<Cupom> cupom = repository.findByCode(code);
        log.info("<< findByCode [cupom isPresente={}]", cupom.isPresent());
        return cupom;
    }

    public Optional<Cupom> isCupomValid(String cupomCode) {

        Optional<Cupom> cupom = findByCode(cupomCode);

        if(!cupom.isPresent()) {
            throw new GlobalException("O cupom informado não é válido");
        }
        if(cupom.get().getCupomStatusEnum().isCanceled()) {
            throw new GlobalException("O cupom informado foi cancelado");
        }
        if(cupom.get().getCupomStatusEnum().isExpired()) {
            throw new GlobalException("O cupom informado passou do prazo de utilização");
        }
        if(cupom.get().getCupomStatusEnum().isApplied()) {
            throw new GlobalException("O cupom informado já foi aplicado a uma compra");
        }
        if(cupom.get().getCupomStatusEnum().isApplied()) {
            throw new GlobalException("O cupom informado já foi utilizado");
        }

        return cupom;
    }

    public Page<CupomView> findByUserAndCupomStatusEnum(String authorization, CupomStatusEnum cupomStatusEnum, Pageable pageable) {
        User user = authenticationService.getUser(authorization);
        log.info(">> findByUser [user Id={}]", user.getId());
        Page<Cupom> cupons = repository.findByUserAndCupomStatusEnum(user, cupomStatusEnum, pageable);
        log.info("<< findByUser [size={}]", cupons.getSize());
        List<CupomView> list = cupons.getContent().stream().map(cupomMapper::entityToView).collect(Collectors.toList());
        return new PageImpl<>(list);
    }

    @Override
    protected JpaRepository<Cupom, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Cupom, CupomView, CupomForm> getConverter() {
        return cupomMapper;
    }

}
