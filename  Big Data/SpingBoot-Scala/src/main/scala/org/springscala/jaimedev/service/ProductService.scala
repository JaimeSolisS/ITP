package org.springscala.jaimedev.service;
import org.springscala.jaimedev.model.Product
import scala.collection.mutable.ListBuffer;
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springscala.jaimedev.repository.ProductRepository
import scala.collection.JavaConversions.iterableAsScalaIterable

@Service
class ProductService @Autowired() (private val productRepository: ProductRepository){

    def addProduct(product: Product): Product = {
      try {
        productRepository.save(product)
      }catch {
        case e: IllegalArgumentException => null
      }
    }

    def getAllProducts(): Seq[Product] ={
      productRepository.findAll().to[Seq]
    }

    def getProduct(id: Int): Product = {
      productRepository.findOne(id)
    }

    def delete(id: Int) = productRepository.delete(id)

    def updateProduct(id: Int, product: Product): Product ={
      product.setId(id)
      try {
        productRepository.save(product)
      }catch {
        case e: IllegalArgumentException => null
      }
    }
}

object ProductList{
  var products = new ListBuffer[Product]()
}



