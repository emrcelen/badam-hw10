package com.garanti.FirstSpringWeb.controller;

import com.garanti.FirstSpringWeb.model.Ders;
import com.garanti.FirstSpringWeb.repo.DersRepo;
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
@RequestMapping(path = "ders")
public class DersController {
    /**
     * IoC -> inversion of controller gibi bir şey
     * Bean yapılması bir nesneyi newleyip application context içerisine atılmasıdır.
     */

    //localhost:9090/FirstSpringWeb/ders/

    private DersRepo repo;

    public DersController()
    {
        this.repo = new DersRepo();
    }

    @GetMapping(path = "getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<ArrayList<Ders>> getAll()
    {
        // localhost:9090/FirstSpringWeb/ders/getAll
        ArrayList<Ders> values = repo.getAll();
        if(values.size() == 0 || values == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<ArrayList<Ders>>(values,"Ders Değerleri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());

    }

    @GetMapping(path = "getByIdHeader", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Ders> getByIdHeader(@RequestHeader(name = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/ders/getByIdHeader
        Ders value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Ders Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).header("id",String.valueOf(id)).build());
    }

    @GetMapping(path = "getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Ders> getByIdQueryParam(@RequestParam(value = "id", required = true) Integer id)
    {
        // localhost:9090/FirstSpringWeb/ders/getById?id=1
        Ders value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Ders Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @GetMapping(path = "getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResult<Ders> getByIdPathParam(@PathVariable(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/ders/getById/1
        //bütün parametreleri vermek zorundayız
        //consume restful servisin dışardan alacağı data türünü belirtir
        //produce web servisin dışarıya vereceği türü belirtir
        Ders value = repo.getById(id);
        if(value == null)
            return new DataResult<>("Veri Seti Boş",ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        return new DataResult<>(value,"Ders Değeri Getirildi.",ResponseEntity.status(HttpStatus.OK).build());
    }
    @DeleteMapping(path = "deleteById/{id}")
    public Result deleteById(@PathVariable(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/ders/deleteById/1
        if (!repo.deleteById(id))
            return new Result("Silinecek Ders Değeri Bulunamadı.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Ders Değeri Başarılı Bir Şekilde Silindi.",ResponseEntity.status(HttpStatus.OK).build());

    }

    @DeleteMapping(path = "deleteByIdHeader")
    public Result deleteByIdHeader(@RequestHeader(value = "id") Integer id)
    {
        // localhost:9090/FirstSpringWeb/ders/deleteById/1
        if (!repo.deleteById(id))
            return new Result("Silinecek Ders Değeri Bulunamadı.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Ders Değeri Başarılı Bir Şekilde Silindi.",ResponseEntity.status(HttpStatus.OK).build());
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result save(@RequestBody Ders ders){
        if(!repo.save(ders))
            return new Result("Eklemeye Çalıştığınız Veri Kümesi Kayıt Edilemedi.",ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build());
        return new Result("Ders Değeri Başarılı Bir Şekilde Kayıt Edildi.",ResponseEntity.status(HttpStatus.ACCEPTED).build());
    }

}
