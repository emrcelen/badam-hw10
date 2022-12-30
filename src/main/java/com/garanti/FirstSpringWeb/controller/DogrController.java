package com.garanti.FirstSpringWeb.controller;

import com.garanti.FirstSpringWeb.model.Ders_Ogrenci;
import com.garanti.FirstSpringWeb.repo.Ders_OgrenciRepo;
import com.garanti.FirstSpringWeb.utilities.results.DataResult;
import com.garanti.FirstSpringWeb.utilities.results.Result;
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

import java.util.ArrayList;

@RestController
@RequestMapping(path = "dogr")
public class DogrController {
    /**
     * IoC -> inversion of controller gibi bir şey
     * Bean yapılması bir nesneyi newleyip application context içerisine atılmasıdır.
     */

    //localhost:9090/FirstSpringWeb/dogr/

    private Ders_OgrenciRepo repo;

    public DogrController()
    {
        this.repo = new Ders_OgrenciRepo();
    }

    @GetMapping(path = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<ArrayList<Ders_Ogrenci>> getAll()
    {
        // localhost:9090/FirstSpringWeb/dogr/getAll
        ArrayList<Ders_Ogrenci> values = repo.getAll();
        if(values.size() == 0 || values == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<ArrayList<Ders_Ogrenci>>(values,"Ders ve Öğrenci Değerleri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());

    }

    @GetMapping(path = "getByIdHeader", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Ders_Ogrenci> getByIdHeader(@RequestHeader(name = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/dogr/getByIdHeader
        Ders_Ogrenci value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Ders ve Öğrenci Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).header("id",String.valueOf(id)).build());
    }

    @GetMapping(path = "getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Ders_Ogrenci> getByIdQueryParam(@RequestParam(value = "id", required = true) Integer id)
    {
        // localhost:9090/FirstSpringWeb/dogr/getById?id=1
        Ders_Ogrenci value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Ders ve Öğrenci Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @GetMapping(path = "getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Ders_Ogrenci> getByIdPathParam(@PathVariable(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/dogr/getById/1
        //bütün parametreleri vermek zorundayız
        //consume restful servisin dışardan alacağı data türünü belirtir
        //produce web servisin dışarıya vereceği türü belirtir
        Ders_Ogrenci value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Ders ve Öğrenci Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());
    }
    @DeleteMapping(path = "deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/dogr/deleteById/1
        if (!repo.deleteById(id))
            return new Result("Silinecek Ders ve Öğrenci Değeri Bulunamadı.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Ders ve Öğrenci Değeri Başarılı Bir Şekilde Silindi.",ResponseEntity.status(HttpStatus.OK).build());

    }

    @DeleteMapping(path = "deleteByIdHeader")
    public Result deleteByIdHeader(@RequestHeader(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/dogr/deleteById/1
        if (!repo.deleteById(id))
            return new Result("Silinecek Ders ve Öğrenci Değeri Bulunamadı.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Ders ve Öğrenci Değeri Başarılı Bir Şekilde Silindi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result save(@RequestBody Ders_Ogrenci dogr){
        if(!repo.save(dogr))
            return new Result("Eklemeye Çalıştığınız Veri Kümesi Kayıt Edilemedi.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Ders ve Öğrenci Değeri Başarılı Bir Şekilde Kayıt Edildi.",ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

}
