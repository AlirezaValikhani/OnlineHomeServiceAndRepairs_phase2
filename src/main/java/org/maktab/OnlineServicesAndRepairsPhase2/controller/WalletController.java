package org.maktab.OnlineServicesAndRepairsPhase2.controller;

import org.dozer.DozerBeanMapper;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.SpecialtyDto;
import org.maktab.OnlineServicesAndRepairsPhase2.dtoClasses.WalletDto;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Wallet;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.WalletServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private WalletServiceImpl walletService;
    private DozerBeanMapper mapper;
    private ModelMapper modelMapper;

    public WalletController(WalletServiceImpl walletService) {
        this.walletService = walletService;
        this.mapper = new DozerBeanMapper();
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/save")
    public ResponseEntity<SpecialtyDto> save(@Valid @RequestBody WalletDto walletDto) {
        Wallet wallet = mapper.map(walletDto, Wallet.class);
        Wallet returnedWallet = walletService.save(wallet);
        SpecialtyDto returnedSpecialtyDto = modelMapper.map(returnedWallet, SpecialtyDto.class);
        return new ResponseEntity<>(returnedSpecialtyDto, HttpStatus.CREATED);
    }
}
