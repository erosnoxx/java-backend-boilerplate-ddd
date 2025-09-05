package com.eterna.backend.unit.objects;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import com.eterna.backend.core.shared.domain.objects.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void shouldCreateMoneyFromBigDecimal() {
        Money money = Money.of(BigDecimal.valueOf(123.456));
        assertThat(money.getFormatted()).isEqualTo("R$ 123.46"); // arredondado
    }

    @Test
    void shouldCreateMoneyFromDouble() {
        Money money = Money.of(99.999);
        assertThat(money.getFormatted()).isEqualTo("R$ 100.00");
    }

    @Test
    void shouldCreateZeroMoney() {
        Money zero = Money.zero();
        assertThat(zero.isZero()).isTrue();
        assertThat(zero.isPositive()).isFalse();
    }

    @Test
    void shouldAddSubtractMultiply() {
        Money m1 = Money.of(50);
        Money m2 = Money.of(20);

        assertThat(m1.add(m2).getFormatted()).isEqualTo("R$ 70.00");
        assertThat(m1.subtract(m2).getFormatted()).isEqualTo("R$ 30.00");
        assertThat(m2.multiply(BigDecimal.valueOf(2.5)).getFormatted()).isEqualTo("R$ 50.00");
    }

    @Test
    void shouldRejectNullMoney() {
        assertThatThrownBy(() -> Money.of((BigDecimal) null))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Money amount cannot be null");
    }
}
