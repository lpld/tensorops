package com.github.lpld.tensorops
import Nat.*
import TensorShape.*
import Tensor.*

object TensorDemo:
  
    val t1: @:[_4, _12 +: _11 +: TensorShape.Scalar] = ???
    val t2: @:[_7, _12 +: _11 +: TensorShape.Scalar] = ???

    // type tt = TensorShape.++[_4 +: _12 +: _11 +: TensorShape.Scalar, _7 +: _12 +: _11 +: TensorShape.Scalar]

    // val t3: tt = Tensor.concat(t1, t2)


