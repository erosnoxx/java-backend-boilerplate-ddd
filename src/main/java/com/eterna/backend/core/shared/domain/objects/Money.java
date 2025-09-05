package com.eterna.backend.core.shared.domain.objects;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import com.eterna.backend.core.shared.domain.objects.common.ValueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money extends ValueObject<BigDecimal> {

    private Money(BigDecimal value) {
        super(value);
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(double amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    @Override
    protected BigDecimal validate(BigDecimal value) {
        if (value == null) {
            throw new DomainException("Money amount cannot be null");
        }

        if (value.scale() > 2) {
            value = value.setScale(2, RoundingMode.HALF_UP);
        }

        return value;
    }

    public Money add(Money other) {
        return new Money(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        return new Money(this.value.subtract(other.value));
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(this.value.multiply(multiplier).setScale(2, RoundingMode.HALF_UP));
    }

    public boolean isPositive() {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isZero() {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public String getFormatted() {
        return String.format("R$ %.2f", value);
    }
}
