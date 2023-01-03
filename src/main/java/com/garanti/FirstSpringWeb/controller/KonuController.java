package com.garanti.FirstSpringWeb.controller;

import com.garanti.FirstSpringWeb.model.Konu;
import com.garanti.FirstSpringWeb.repo.KonuRepo;
import com.garanti.FirstSpringWeb.utilities.results.DataResult;
import com.garanti.FirstSpringWeb.utilities.results.Result;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "konu")
@AllArgsConstructor
public class KonuController {
    /**
     * IoC -> inversion of controller gibi bir şey
     * Bean yapılması bir nesneyi newleyip application context içerisine atılmasıdır.
     */

    //localhost:9090/FirstSpringWeb/konu/

    private KonuRepo repo;


    @GetMapping(path = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<List<Konu>> getAll()
    {
        // localhost:9090/FirstSpringWeb/konu/getAll
        List<Konu> values = repo.getAll();
        if(values.size() == 0 || values == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<List<Konu>>(values,"Konular Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());

    }

    @GetMapping(path = "getByIdHeader", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Konu> getByIdHeader(@RequestHeader(name = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/konu/getByIdHeader
        Konu value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Konu Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).header("id",String.valueOf(id)).build());
    }

    @GetMapping(path = "getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Konu> getByIdQueryParam(@RequestParam(value = "id", required = true) Integer id)
    {
        // localhost:9090/FirstSpringWeb/konu/getById?id=1
        Konu value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Konu Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @GetMapping(path = "getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Konu> getByIdPathParam(@PathVariable(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/konu/getById/1
        //bütün parametreleri vermek zorundayız
        //consume restful servisin dışardan alacağı data türünü belirtir
        //produce web servisin dışarıya vereceği türü belirtir
        Konu value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Konu Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @GetMapping(path = "getNameLike", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<List<Konu>> getNameLike (@RequestParam(value = "name", required = true) String name){
        return new DataResult<>(repo.getAllLike(name),"Girdiğiniz Parametre ile Eşlesen Konu Bilgileri Getirildi", ResponseEntity.status(HttpStatus.OK).build());
    }
    @DeleteMapping(path = "deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/konu/deleteById/1
        if (!repo.deleteById(id))
            return new Result("Silinecek Konu Değeri Bulunamadı.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Konu Değeri Başarılı Bir Şekilde Silindi.",ResponseEntity.status(HttpStatus.OK).build());

    }

    @DeleteMapping(path = "deleteByIdHeader")
    public Result deleteByIdHeader(@RequestHeader(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/konu/deleteById/1
        if (!repo.deleteById(id))
            return new Result("Silinecek Konu Değeri Bulunamadı.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Konu Değeri Başarılı Bir Şekilde Silindi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result save(@RequestBody Konu konu){
        if(!repo.save(konu))
            return new Result("Eklemeye Çalıştığınız Veri Kümesi Kayıt Edilemedi.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Konu Değeri Başarılı Bir Şekilde Kayıt Edildi.",ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

}
