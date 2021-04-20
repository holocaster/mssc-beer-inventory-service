package guru.sfg.beer.inventory.service.listerners;

import br.com.prcompany.beerevents.events.AllocateOrderRequest;
import br.com.prcompany.beerevents.events.AllocateOrderResult;
import br.com.prcompany.beerevents.utils.EventsConstants;
import guru.sfg.beer.inventory.service.services.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AllocationOrderListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = EventsConstants.ALLOCATE_ORDER_QUEUE)
    public void listenAllocateOrder(@Payload AllocateOrderRequest allocateOrderRequest) {
        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder().beerOrderDTO(allocateOrderRequest.getBeerOrderDTO());

        try {
            final boolean allocateOrder = this.allocationService.allocateOrder(allocateOrderRequest.getBeerOrderDTO());
            builder.pendingInventory(!allocateOrder);
        } catch (Exception e) {
            log.error("Allocation failed: {}", e.getMessage());
            builder.allocationError(true);
        }
        this.jmsTemplate.convertAndSend(EventsConstants.ALLOCATE_ORDER_RESULT_QUEUE, builder.build());
    }
}
