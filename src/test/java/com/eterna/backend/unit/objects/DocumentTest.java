package com.eterna.backend.unit.objects;

import com.eterna.backend.core.shared.domain.exceptions.DomainException;
import com.eterna.backend.core.shared.domain.objects.Document;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentTest {

    // ----- CPF válido -----
    @Test
    void shouldCreateValidCPF() {
        String cpf = "529.982.247-25"; // CPF válido
        Document doc = Document.of(cpf);

        assertThat(doc.isCPF()).isTrue();
        assertThat(doc.isCNPJ()).isFalse();
        assertThat(doc.getFormatted()).isEqualTo("529.982.247-25");
    }

    // ----- CPF inválido -----
    @Test
    void shouldRejectInvalidCPF() {
        String invalidCpf = "111.111.111-11";

        assertThatThrownBy(() -> Document.of(invalidCpf))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("invalid cpf");
    }

    // ----- CNPJ válido -----
    @Test
    void shouldCreateValidCNPJ() {
        String cnpj = "04.470.781/0001-39"; // CNPJ válido
        Document doc = Document.of(cnpj);

        assertThat(doc.isCNPJ()).isTrue();
        assertThat(doc.isCPF()).isFalse();
        assertThat(doc.getFormatted()).isEqualTo("04.470.781/0001-39");
    }

    // ----- CNPJ inválido -----
    @Test
    void shouldRejectInvalidCNPJ() {
        String invalidCnpj = "11.111.111/1111-11";

        assertThatThrownBy(() -> Document.of(invalidCnpj))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("invalid cnpj");
    }

    // ----- Documento com tamanho errado -----
    @Test
    void shouldRejectDocumentWithWrongLength() {
        String shortDoc = "123456";

        assertThatThrownBy(() -> Document.of(shortDoc))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("document must be CPF");
    }

    // ----- Testar limpeza de caracteres -----
    @Test
    void shouldCleanNonDigits() {
        String cpf = "529-982.247/25";
        Document doc = Document.of(cpf);

        assertThat(doc.getFormatted()).isEqualTo("529.982.247-25");
    }
}
