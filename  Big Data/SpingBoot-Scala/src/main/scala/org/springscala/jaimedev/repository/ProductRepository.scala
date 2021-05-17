package org.springscala.jaimedev.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springscala.jaimedev.model.Product
import java.lang.Long

@Repository
trait ProductRepository extends CrudRepository[Product, Long]
