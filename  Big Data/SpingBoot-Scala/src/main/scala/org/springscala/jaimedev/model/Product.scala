package org.springscala.jaimedev.model
/*
class Product(
              name: String
              /*photURL: String, 
              stock: Int, 
              price: Float,
              category: String, 
              varient: String, 
              show: Boolean*/
)
{}*/

import javax.persistence.{Entity, GeneratedValue, GenerationType, Id, Table}
import scala.annotation.meta.field
import scala.beans.BeanProperty
import org.hibernate.validator.constraints.NotEmpty

@Entity
@Table(name = "Products")
class Product(){
  @(Id @field)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @BeanProperty
  var id: Long = _

  @BeanProperty
  @(NotEmpty @field)
  var name: String = _


}





