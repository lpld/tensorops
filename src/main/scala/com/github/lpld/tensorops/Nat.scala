package com.github.lpld.tensorops

enum Nat :
  case Zero()
  case Succ[N <: Nat]()

object Nat :
    
  infix trait <[A <: Nat, B <: Nat]
  object < :
    def apply[A <: Nat, B <: Nat]( using lt: A < B ): A < B = lt
    given basic[A <: Nat]: <[_0, Succ[A]] with {}
    given inductive[A <: Nat, B <: Nat](using lt: A < B): <[Succ[A], Succ[B]] with {}

  infix trait >[A <: Nat, B <: Nat]
  object > :
    def apply[A <: Nat, B <: Nat]( using lt: A > B ): A > B = lt
    given inverseLt[A <: Nat, B <: Nat](using B < A): >[A, B] with {}

  infix trait <=[A <: Nat, B <: Nat]
  object <= :
    def apply[A <: Nat, B <: Nat]( using le: A <= B ): A <= B = le
    given basic[A <: Nat]: <=[_0, A] with {}
    given inductive[A <: Nat, B <: Nat](using lt: A <= B): <=[Succ[A], Succ[B]] with {}

  infix trait >=[A <: Nat, B <: Nat]
  object >= :
    def apply[A <: Nat, B <: Nat]( using le: A >= B ): A >= B = le
    given inverseLe[A <: Nat, B <: Nat](using B <= A): >=[A, B] with {}

  infix type +[A <: Nat, B <: Nat] <: Nat = (A, B) match 
    case (Zero, ?) => B
    case (Succ[a], b) => Succ[a + b]

  infix type *[A <: Nat, B <: Nat] <: Nat = (A, B) match 
    case (Zero, ?) => Zero
    case (Succ[a], b) => (a * b) + b

  infix type -[A <: Nat, B <: Nat] <: Nat = (A, B) match 
    case (?, Zero) => A
    case (Succ[a], Succ[b]) => a - b
  
  type Pred[N <: Nat] <: Nat = N match 
    case Succ[n] => n

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