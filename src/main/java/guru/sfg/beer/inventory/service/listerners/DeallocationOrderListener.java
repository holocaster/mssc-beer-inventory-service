package guru.sfg.beer.inventory.service.listerners;

import br.com.prcompany.beerevents.events.DeallocateOrderRequest;
import br.com.prcompany.beerevents.utils.EventsConstants;
import guru.sfg.beer.inventory.service.services.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationOrderListener {

    private final AllocationService allocationService;

    @JmsListener(destination = EventsConstants.DEALLOCATE_ORDER_QUEUE)
    public void listen(@Payload DeallocateOrderRequest request) {
        this.allocationService.deallocateOrder(request.getBeerOrderDTO());
    }
}
