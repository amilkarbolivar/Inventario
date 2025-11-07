package com.inventario.dto.cliente;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ClienteDTO {
     private Long id;
     private String documento_tipo;
     private  String cedula;
     private Long supermercadoId;

}
