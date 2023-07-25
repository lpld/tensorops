package com.github.lpld.tensorops

import Nat.*

/** Represents a natural number in Peano encoding, i.e. each number is represented */
enum Nat :
  case Zero() extends Nat
  case Succ[P <: Nat]() extends Nat

  type Plus[That <: Nat] = +[this.type, That]#Result

object Nat :

  trait <[A <: Nat, B <: Nat]
  object < :
    def apply[A <: Nat, B <: Nat]( using lt: A < B ): A < B = lt
    given basic[A <: Nat]: <[_0, Succ[A]] with {}
    given inductive[A <: Nat, B <: Nat](using lt: A < B): <[Succ[A], Succ[B]] with {}

  trait >[A <: Nat, B <: Nat]
  object > :
    def apply[A <: Nat, B <: Nat]( using lt: A > B ): A > B = lt
    given inverseLt[A <: Nat, B <: Nat](using B < A): >[A, B] with {}

  trait <=[A <: Nat, B <: Nat]
  object <= :
    def apply[A <: Nat, B <: Nat]( using le: A <= B ): A <= B = le
    given basic[A <: Nat]: <=[_0, A] with {}
    given inductive[A <: Nat, B <: Nat](using lt: A <= B): <=[Succ[A], Succ[B]] with {}

  trait >=[A <: Nat, B <: Nat]
  object >= :
    def apply[A <: Nat, B <: Nat]( using le: A >= B ): A >= B = le
    given inverseLe[A <: Nat, B <: Nat](using B <= A): >=[A, B] with {}

  trait Sum[A <: Nat, B <: Nat, C <: Nat]
  object Sum :
    given zeroPlusZero: Sum[_0, _0, _0] with {}
    given zeroPlusA[A <: Nat]: Sum[_0, Succ[A], Succ[A]] with {}
    given aPlusZero[A <: Nat]: Sum[Succ[A], _0, Succ[A]] with {}

    given aPlusB[A <: Nat, B <: Nat, C <: Nat](using Sum[A, B, C]): Sum[Succ[A], Succ[B], Succ[Succ[C]]] with {}

  trait +[A <: Nat, B <: Nat] { type Result <: Nat }
  object + :
    def apply[A <: Nat, B <: Nat](using sum: +[A, B]) = sum
    given result[A <: Nat, B <: Nat, C <: Nat](using Sum[A, B, C]): +[A, B] with { type Result = C }

  trait -[A <: Nat, B <: Nat] { type Result <: Nat }
  object - :
    def apply[A <: Nat, B <: Nat](using diff: -[A, B]) = diff
    given result[A <: Nat, B <: Nat, C <: Nat](using Sum[B, C, A]): -[A, B] with { type Result = C }

  trait Mul[A <: Nat, B <: Nat, C <: Nat]
  object Mul :
    given zeroTimesZero: Mul[_0, _0, _0] with {}
    given zeroTimesA[A <: Nat]: Mul[_0, Succ[A], _0] with {}
    given aTimesZero[A <: Nat]: Mul[Succ[A], _0, _0] with {}

    // (a + 1) * (b + 1) = ab + a + b + 1
    given aTimesB[A <: Nat, B <: Nat, AB <: Nat, AB_A <: Nat, AB_A_B <: Nat](using Mul[A, B, AB], Sum[AB, A, AB_A], Sum[AB_A, B, AB_A_B]): Mul[Succ[A], Succ[B], Succ[AB_A_B]] with {}

  trait *[A <: Nat, B <: Nat] { type Result <: Nat }
  object * :
    def apply[A <: Nat, B <: Nat](using mul: *[A, B]) = mul
    given result[A <: Nat, B <: Nat, C <: Nat](using Mul[A, B, C]): *[A, B] with { type Result = C }

  trait /[A <: Nat, B <: Nat] { type Result <: Nat }
  object / :
    def apply[A <: Nat, B <: Nat](using div: /[A, B]) = div
    // Doesn't work!
    given result[A <: Nat, B <: Nat, C <: Nat](using Mul[B, C, A]): /[A, B] with { type Result = C }

  trait Min[A <: Nat, B <: Nat] { type Result <: Nat }
  object Min :
    given result[A <: Nat, B <: Nat](using A <= B): Min[A, B] with { type Result = A }

    type R[A <: Nat, B <: Nat] = Min[A, B]#Result

  type _0 = Zero
  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]
  type _5 = Succ[_4]
  type _6 = Succ[_5]
  type _7 = Succ[_6]
  type _8 = Succ[_7]
  type _9 = Succ[_8]
  type _10 = Succ[_9]
  type _11 = Succ[_10]
  type _12 = Succ[_11]
  type _13 = Succ[_12]
  type _14 = Succ[_13]
  type _15 = Succ[_14]
  type _16 = Succ[_15]
  type _17 = Succ[_16]
  type _18 = Succ[_17]
  type _19 = Succ[_18]
  type _20 = Succ[_19]
  type _21 = Succ[_20]
  type _22 = Succ[_21]
  type _23 = Succ[_22]
  type _24 = Succ[_23]
  type _25 = Succ[_24]
  type _26 = Succ[_25]
  type _27 = Succ[_26]
  type _28 = Succ[_27]
  type _29 = Succ[_28]
  type _30 = Succ[_29]
