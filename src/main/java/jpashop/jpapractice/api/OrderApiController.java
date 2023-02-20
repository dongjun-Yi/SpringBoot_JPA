package jpashop.jpapractice.api;

import jpashop.jpapractice.domain.Order;
import jpashop.jpapractice.domain.service.OrderService;
import jpashop.jpapractice.dto.BasicResponse;
import jpashop.jpapractice.dto.order.OrderDto;
import jpashop.jpapractice.dto.order.SimpleOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderService orderService;

    @GetMapping("/api/simple-orders")
    public ResponseEntity<BasicResponse> orders() {
        List<Order> orders = orderService.findOrdersWithMemberDelivery();
        List<SimpleOrderDto> collect = orders.stream().map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new BasicResponse(collect), HttpStatus.OK);
    }

    @GetMapping("/api/orders")
    public ResponseEntity<BasicResponse> ordersWithOrderItem() {
        List<Order> orders = orderService.findAllOrders();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(Collectors.toList());
        return new ResponseEntity<>(new BasicResponse(collect), HttpStatus.OK);
    }

    @GetMapping("/api/ordersItem") //쿼리 한번
    public ResponseEntity<BasicResponse> ordersWithItem() {
        List<Order> orders = orderService.findALlWithItem();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new BasicResponse(collect), HttpStatus.OK);
    }
//
//    @GetMapping("/api/ordersItem-page") //쿼리 3번
//    public ResponseEntity<BasicResponse> ordersWithOrderItem_Page(@RequestParam(value = "offset", defaultValue = "0") int offset,
//                                                                  @RequestParam(value = "limit", defaultValue = "100") int limit
//    ) {
//        List<Order> orders = orderService.findOrdersWithMemberDelivery(offset, limit);
//        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(new BasicResponse(collect), HttpStatus.OK);
//    }

    @GetMapping("/api/ordersItem-page")
    public ResponseEntity<BasicResponse> ordersWithOrderItem_Page(Pageable pageable) {
        System.out.println(pageable.getOffset() + " " + pageable.getPageSize());
        List<Order> orders = orderService.findOrdersWithMemberDelivery(pageable);
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new BasicResponse(collect), HttpStatus.OK);
    }
}
