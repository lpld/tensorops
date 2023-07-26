package com.github.lpld.tensorops
import Nat.*
import TensorShape.*
import Tensor.*

object TensorDemo:
  
    val t1: _4 @: (_12 +: SNil) = ???
    val t2: _3 @: (_12 +: SNil) = ???
    type s2 = _3 +: _12 +: SNil 
    type s3 = _3 +: _16 +: SNil 

    t1.reshape[s3]

    val a = t1 ++ t2
// 
    // type tt = TensorShape.++[_4 +: _12 +: _11 +: TensorShape.Scalar, _7 +: _12 +: _11 +: TensorShape.Scalar]

    // val t3: tt = Tensor.concat(t1, t2)


