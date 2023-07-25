package com.github.lpld.tensorops
import Nat.*

sealed trait LList[L <: Nat, +A] :
  type Length = L

  def #:[B >: A](b: B): LCons[L, B] = LCons(b, this)

  def concat[B >: A, L2 <: Nat](that: LList[L2, B]): LList[L + L2, B]  

  def map[B](f: A => B): LList[L, B] = this match
    case LNil            => LNil
    case LCons(head, tail) => LCons(f(head), tail.map(f))

  def zipWith[B, C](other: LList[L, B], f: (A, B) => C): LList[L, C] = (this, other) match
    case (LNil, LNil)               => LNil
    case (LCons(h1, t1), LCons(h2, t2)) => new LCons(f(h1, h2), t1.zipWith(t2, f))

case object LNil extends LList[_0, Nothing] :
  override def concat[B >: Nothing, L2 <: Nat](that: LList[L2, B]): LList[L2, B] = that

type LNil = LNil.type

case class LCons[TL <: Nat,  +A](head: A, tail: LList[TL, A]) extends LList[Succ[TL], A] :
  override def concat[B >: A, L2 <: Nat](that: LList[L2, B]): LList[Succ[TL + L2], B] = LCons(head, tail.concat(that))
