package com.smartcity.mapperDto;

import com.smartcity.config.ProfileConfig;
import com.smartcity.dao.BaseTest;
import com.smartcity.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
class TransactionDtoMapperTest extends BaseTest {

    private Transaction transaction;

    @Autowired
    private TransactionDtoMapper mapper;

    @BeforeEach
    public void init() {
        transaction = new Transaction(1L, 1L, 30000L, 2000L,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void testConvertDaoToDto() {
        assertThat(transaction).isEqualToIgnoringGivenFields(mapper.transactionToTransactionDto(transaction));
    }

    @Test
    public void testConvertDtoToDao() {
        assertThat(mapper.transactionToTransactionDto(transaction)).isEqualToIgnoringGivenFields(transaction);
    }

}