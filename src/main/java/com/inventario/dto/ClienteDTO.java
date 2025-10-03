package com.inventario.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ClienteDTO {
     private int id;
     private String documento_tipo;
     private  String cedula;
     private Long supermercadoId;
     private String supermercadoNombre;

}
