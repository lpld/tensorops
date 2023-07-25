package com.github.lpld.tensorops.v2
import scala.compiletime.ops.int.*
import scala.compiletime.ops.any.*
import scala.compiletime.*

sealed trait SList[S <: Int, +A] :
    type Length = S

    def #::[B >: A](b: B): SList[S + 1, B] = SCons(b, this)

    def map[B](f: A => B): SList[S, B] = this match
        case SNil              => SNil
        case SCons(head, tail) => SCons(f(head), tail.map(f))

    def zipWith[B, C](other: SList[S, B], f: (A, B) => C): SList[S, C] = (this, other) match
        case (SNil, SNil)                   => SNil
        case (SCons(h1, t1), SCons(h2, t2)) => SCons(f(h1, h2), t1.zipWith(t2, f))

    // def concat[S2 <: Int, B >: A](other: SList[S2, B]): SList[S + S2, B]

case object SNil extends SList[0, Nothing] 
type SNil = SNil.type
    // def concat[S2 <: Int, B >: Nothing](other: SList[S2, B]): SList[0 + S2, B] = other
case class SCons[TailLength <: Int, +A](head: A, tail: SList[TailLength, A]) extends SList[TailLength + 1, A]

object SList :
    type Concat[L1 <: Int, L2 <: Int, A, X <: SList[L1, A], Y <: SList[L2, A]] = (X, Y) match 
        case (SNil, s2) => s2
        case (s1, SNil) => s1
        case (SCons[tl1, a], SCons[tl2, b]) => SCons[tl1 + tl2 + 1, a | b]

    