package br.com.mozar7.mysnack.delivery.tracking.domain.repository;

import br.com.mozar7.mysnack.delivery.tracking.domain.model.ContactPoint;
import br.com.mozar7.mysnack.delivery.tracking.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void shouldPersist() {
        Delivery delivery = Delivery.draft();

        delivery.editPreparationDetails(createValidPreparationDetails());

        delivery.addItem("Computador", 2);
        delivery.addItem("Notebook", 2);

        deliveryRepository.saveAndFlush(delivery);
        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();

        assertEquals(2, persistedDelivery.getItems().size());

    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-000")
                .street("Rua General dos Testes")
                .number("100")
                .complement("Sala 123")
                .name("João Tester")
                .phone("(11) 98000-0000")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("12345-678")
                .street("Rua Marechal dos Testes")
                .number("987")
                .complement("Sala 321")
                .name("Maria Tester")
                .phone("(11) 98888-7777")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("15.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}