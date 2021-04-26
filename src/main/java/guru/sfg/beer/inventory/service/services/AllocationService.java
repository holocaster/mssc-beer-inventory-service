package guru.sfg.beer.inventory.service.services;

import br.com.prcompany.beerevents.model.BeerOrderDTO;

public interface AllocationService {

    boolean allocateOrder(BeerOrderDTO beerOrderDTO);

    void deallocateOrder(BeerOrderDTO beerOrderDTO);
}
