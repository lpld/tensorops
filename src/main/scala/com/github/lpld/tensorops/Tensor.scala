package com.github.lpld.tensorops
import Nat.*
import TensorShape.*

enum TensorShape :
  case SNil()
  case +:[Dim <: Nat, Tail <: TensorShape]()

case object TensorShape :
  type Rank[Shape <: TensorShape] <: Nat = Shape match 
    case SNil      => _0
    case +:[?, tail] => Succ[Rank[tail]]

  type Vector[N <: Nat] = N +: SNil
  type Matrix[M <: Nat, N <: Nat] = M +: Vector[N]

  type Size[Shape <: TensorShape] <: Nat = Shape match
    case SNil => _1
    case +:[dim, tail] => dim * Size[tail]

  type Similar[S1 <: TensorShape, S2 <: TensorShape] = (S1, S2) match
    case (SNil, SNil)                   => SNil
    case (+:[Zero, tail1], +:[Zero, tail2]) => +:[Zero, Similar[tail1, tail2]]
    case (+:[Succ[dim1], tail1], +:[Succ[dim2], tail2]) => ExtendDim[Similar[+:[dim1, tail1], +:[dim2, tail2]]]
  
  type ExtendDim[S <: TensorShape] = S match 
    case +:[dim, tail] => +:[Succ[dim], tail]

  infix type ++[This <: TensorShape, That <: TensorShape] = (This, That) match 
    case (+:[dim1, tail1], +:[dim2, tail2]) => +:[dim1 + dim2, Similar[tail1, tail2]]

enum Tensor[Shape <: TensorShape] :
  case Scalar(value: Double) extends Tensor[SNil]
  case @:[Dim <: Nat, TailShape <: TensorShape](components: LList[Dim, Tensor[TailShape]]) extends Tensor[Dim +: TailShape]

  def asCons[Dim <: Nat, TailShape <: TensorShape](using Shape =:= Dim +: TailShape) = this.asInstanceOf[Dim @: TailShape]

  def elementwiseMap(f: Double => Double): Tensor[Shape] = this match
      case Scalar(v) => Scalar(f(v))
      case @:(cs)    => @:(cs.map(_.elementwiseMap(f)))

  def elementwiseCombine(other: Tensor[Shape], combine: (Double, Double) => Double): Tensor[Shape] = (this, other) match
      case (Scalar(v1), Scalar(v2)) => Scalar(combine(v1, v2))
      case (@:(cs1), @:(cs2))       => @:(cs1.zipWith(cs2, _.elementwiseCombine(_, combine)))

  /** Tensor-scalar product */
  def *(scalar: Double): Tensor[Shape] = this.elementwiseMap(_ * scalar)

  /** Elementwise tensor sum */
  def +(other: Tensor[Shape]): Tensor[Shape] = this.elementwiseCombine(other, _ + _)

  /** Elementwise tensor product */
  def *(other: Tensor[Shape]): Tensor[Shape] = this.elementwiseCombine(other, _ * _)

  def ++[Dim1 <: Nat, Dim2 <: Nat, TailShape <: TensorShape](that: Tensor[Dim2 +: TailShape])(using ev: Tensor[Shape] =:= Tensor[Dim1 +: TailShape]) =
     Tensor.concat(ev(this), that)

  def reshape[NewShape <: TensorShape](using Size[Shape] =:= Size[NewShape]): Tensor[NewShape] = ???

object Tensor :
  def concat[Dim1 <: Nat, Dim2 <: Nat, TailShape <: TensorShape](
    a: Tensor[Dim1 +: TailShape],
    b: Tensor[Dim2 +: TailShape]
  ): (Dim1 + Dim2) @: TailShape = @:(a.asCons.components.concat(b.asCons.components))
