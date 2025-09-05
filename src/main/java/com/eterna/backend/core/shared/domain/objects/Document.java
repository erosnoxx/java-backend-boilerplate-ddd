package com.eterna.backend.core.shared.domain.objects;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import com.eterna.backend.core.shared.domain.objects.common.StringValueObject;

public final class Document extends StringValueObject {

    private Document(String value) {
        super(value);
    }

    public static Document of(String document) {
        return new Document(document);
    }

    @Override
    protected String customValidate(String value) {
        String cleanValue = value.replaceAll("[^0-9]", "");

        if (cleanValue.length() == 11) {
            if (!isValidCPF(cleanValue)) {
                throw new DomainException("invalid cpf: " + value);
            }
            return cleanValue;
        }

        if (cleanValue.length() == 14) {
            if (!isValidCNPJ(cleanValue)) {
                throw new DomainException("invalid cnpj: " + value);
            }
            return cleanValue;
        }

        throw new DomainException(
                "document must be CPF (11 digits) or CNPJ (14 digits)"
        );
    }

    public boolean isCPF() {
        return value.length() == 11;
    }

    public boolean isCNPJ() {
        return value.length() == 14;
    }

    public String getFormatted() {
        if (isCPF()) {
            return String.format("%s.%s.%s-%s",
                    value.substring(0, 3),
                    value.substring(3, 6),
                    value.substring(6, 9),
                    value.substring(9, 11)
            );
        }

        return String.format("%s.%s.%s/%s-%s",
                value.substring(0, 2),
                value.substring(2, 5),
                value.substring(5, 8),
                value.substring(8, 12),
                value.substring(12, 14)
        );
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        return validateDocument(cpf, new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2}, 9) &&
                validateDocument(cpf, new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2}, 10);
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        return validateDocument(cnpj, new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}, 12) &&
                validateDocument(cnpj, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}, 13);
    }

    /**
     * Validates a check digit using a modular algorithm.
     *
     * @param document full document number
     * @param weights  weights used for multiplication
     * @param digitPosition position of the digit to validate
     * @return true if the digit is valid
     */
    private boolean validateDocument(String document, int[] weights, int digitPosition) {
        int sum = 0;

        for (int i = 0; i < weights.length; i++) {
            int digit = Character.getNumericValue(document.charAt(i));
            sum += digit * weights[i];
        }

        int remainder = sum % 11;
        int expectedDigit = (remainder < 2) ? 0 : (11 - remainder);
        int actualDigit = Character.getNumericValue(document.charAt(digitPosition));

        return expectedDigit == actualDigit;
    }
}
