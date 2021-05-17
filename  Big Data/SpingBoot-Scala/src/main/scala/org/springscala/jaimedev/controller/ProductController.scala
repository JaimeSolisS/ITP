package org.springscala.jaimedev.controller;

import java.lang.Iterable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController 
import org.springframework.http.HttpStatus
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.CrossOrigin

import org.springscala.jaimedev.model.Product;
import org.springscala.jaimedev.service.ProductService;
import scala.collection.mutable.ListBuffer

import java.util
import scala.collection.JavaConversions._


@RestController
@CrossOrigin(origins = Array("*"), allowedHeaders = Array("*"))
@RequestMapping(path = Array("/api"))
class ProductController(@Autowired productService: ProductService){


    @PostMapping(path = Array("/products"))
   def postProduct(@RequestBody product: Product ): ResponseEntity[Product] ={
      val newProduct = productService.addProduct(product);
      if(newProduct != null)
         new ResponseEntity(newProduct, new HttpHeaders, HttpStatus.CREATED)
      else
         new ResponseEntity(null, new HttpHeaders, HttpStatus.NOT_MODIFIED)
   }

   @GetMapping(path = Array("/products"))
   def getAllProducts(): ResponseEntity[java.util.ArrayList[Product]] = {
      val productList = new java.util.ArrayList[Product](productService.getAllProducts)
      new ResponseEntity(productList , new HttpHeaders, HttpStatus.OK)
   }

   @GetMapping(path = Array("/products/{id}"))
   def getProduct(@PathVariable id: Int): ResponseEntity[Product] = {
      val product = productService.getProduct(id)
      if(product != null)
         new ResponseEntity(product, new HttpHeaders, HttpStatus.OK)
      else
         new ResponseEntity(null, new HttpHeaders, HttpStatus.NOT_FOUND)
   }

   @PutMapping(path = Array("/products/{id}"))
   def updateProduct(@PathVariable id: Int, @RequestBody product: Product): ResponseEntity[Product] ={
      val updatedProduct = productService.updateProduct(id, product);
      if(updatedProduct != null)
         new ResponseEntity(updatedProduct, new HttpHeaders, HttpStatus.ACCEPTED)
      else
         new ResponseEntity(null, new HttpHeaders, HttpStatus.NOT_MODIFIED)
   }

   @DeleteMapping(path = Array("/products/{id}"))
   def deletePost(@PathVariable id: Int) = productService.delete(id)
   
}

