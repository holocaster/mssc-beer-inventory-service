package guru.sfg.beer.inventory.service.services;

import br.com.prcompany.beerevents.events.NewInventoryEvent;
import br.com.prcompany.beerevents.utils.EventsConstants;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = EventsConstants.NEW_INVENTORY_QUEUE)
    public void listen(@Payload NewInventoryEvent event) {
        log.debug("Got inventory: " + event.toString());

        this.beerInventoryRepository.save(BeerInventory.builder().
                beerId(event.getBeerDTO().getId())
                .upc(event.getBeerDTO().getUpc())
                .quantityOnHand(event.getBeerDTO().getQuantityOnHand()).build());
    }
}
