package com.garanti.FirstSpringWeb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ders
{
    private Integer ID;

    // bu bir foreign key 'dir
    @NonNull
    private Integer OGR_ID;

    // bu bir foreign key 'dir
    @NonNull
    private Integer KONU_ID;
}
