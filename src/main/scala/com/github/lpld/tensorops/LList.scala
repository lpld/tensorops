package com.github.lpld.tensorops
import Nat.*

/** Linked list that tracks its length on the type level*/
sealed trait LList[+A, L <: Nat] :
    type Length = L

    def map[B](f: A => B): LList[B, L] = this match
        case LNil            => LNil
        case #::(head, tail) => new #::(f(head), tail.map(f))

    def zipWith[B, C](other: LList[B, L], f: (A, B) => C): LList[C, L] = (this, other) match
        case (#::(h1, t1), #::(h2, t2)) => new #::(f(h1, h2), t1.zipWith(t2, f))
        case (LNil, LNil)               => LNil

    def #::[B >: A](b: B): LList[B, Nat.Succ[L]] = new #::(b, this)

    // def contact[L2 <: Nat, L3 <: Nat, A](l2: LList[A, L2])(using s: Sum[L, L2, L3]): LList[A, L3]
    // def +++[B >: A, L2 <: Nat](bs: LList[B, L2])(using l: L + L2): LList[B, l.Result] = this match
    //     case LNil => this
    //     case #::(head, tail) => new #::(head, tail +++ bs)

    // def concat[L2 <: Nat, L3 <: Nat, B >: A](other: LList[B, L2])(using Sum[L, L2, L3]): LList[B, L3]


case object LNil extends LList[Nothing, Nat.Zero] 
type LNil = LNil.type
case class #::[+A, TailLength <: Nat](head: A, tail: LList[A, TailLength]) extends LList[A, Nat.Succ[TailLength]]

    // def contact[L2 <: Nat, L3 <: Nat, A](l2: LList[A, L2]): LList[A, L3] = new #::(head, tail.contact(l2)(using ???))
// object LList :
    // type Concat[X <: LList[?, ?], Y <: LList[?, ?]] = X match 
    //     case LNil               => Y
    //     case #::[a, Zero]       => #::[a, Succ[Y#Length]]
    //     case #::[a, Succ[l]]    => Concat[Concat[#::[a, l], Y], #::[a, _1]]
