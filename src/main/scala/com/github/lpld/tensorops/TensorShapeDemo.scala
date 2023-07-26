package com.github.lpld.tensorops
import Nat.*
import TensorShape.*

object TensorShapeDemo :

    type M1 = Matrix[_12, _14]
    type M2 = Matrix[_14, _12]


    type M3 = Matrix[_14, _12]

    type s2 = Similar[M2, M3]

    type M4 = Matrix[_4, _5] ++ Matrix[_9, _5] 
    type VV = Vector[_12] ++ Vector[_13]


    summon[Vector[_12] ++ Vector[_13] =:= Vector[_25]]
    summon[Matrix[_2, _17] ++ Matrix[_14, _17] =:= Matrix[_16, _17]]
    type A = (_10 +: _2 +: _19 +: SNil) ++ (_10 +: _2 +: _19 +: SNil)
    summon[(_10 +: _2 +: _19 +: SNil) ++ (_10 +: _2 +: _19 +: SNil) =:= (_20 +: _2 +: _19 +: SNil)]

    summon[Size[_3 +: _4 +: _2 +: SNil] =:= _24]
    
    