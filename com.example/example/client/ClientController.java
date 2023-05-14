package com.example.client;

@RestController
@RequestMapping("/orders")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderDto orderDto) {
        String orderId = clientService.placeOrder(orderDto);
        return ResponseEntity.ok(orderId);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String orderId) {
        OrderDto orderDto = clientService.getOrder(orderId);
        return ResponseEntity.ok(orderDto);
    }
}
