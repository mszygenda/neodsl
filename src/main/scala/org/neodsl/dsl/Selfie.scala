package org.neodsl.dsl

trait Selfie[T] {
  def self: T = {
    this.asInstanceOf[T]
  }
}
