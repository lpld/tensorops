package com.github.lpld.tensorops
import Nat.*

enum TensorShape :

  case Scalar()
  case +:[Dim <: Nat, Tail <: TensorShape]()

case object TensorShape :
  type Rank[Shape <: TensorShape] = Shape match 
    case Scalar      => _0
    case +:[?, tail] => Succ[Rank[tail]]

  type Vector[N <: Nat] = N +: Scalar
  type Matrix[M <: Nat, N <: Nat] = M +: Vector[N]

  type Similar[S1 <: TensorShape, S2 <: TensorShape] = (S1, S2) match
    case (Scalar, Scalar)                   => Scalar
    case (+:[Zero, tail1], +:[Zero, tail2]) => +:[Zero, Similar[tail1, tail2]]
    case (+:[Succ[dim1], tail1], +:[Succ[dim2], tail2]) => ExtendDim[Similar[+:[dim1, tail1], +:[dim2, tail2]]]
  
  type ExtendDim[S <: TensorShape] = S match 
    case +:[dim, tail] => +:[Succ[dim], tail]

  infix type ++[This <: TensorShape, That <: TensorShape] = (This, That) match 
    case (+:[dim1, tail1], +:[dim2, tail2]) => +:[dim1 + dim2, TensorShape.Similar[tail1, tail2]]

enum Tensor[S <: TensorShape] :
  case Scalar(value: Double) extends Tensor[TensorShape.Scalar]
  case @:[Dim <: Nat, TailShape <: TensorShape](components: LList[Dim, Tensor[TailShape]]) extends Tensor[TensorShape.+:[Dim, TailShape]]

  def elementwiseMap(f: Double => Double): Tensor[S] = this match
      case Scalar(v) => Scalar(f(v))
      case @:(cs)    => @:(cs.map(_.elementwiseMap(f)))

  def elementwiseCombine(other: Tensor[S], combine: (Double, Double) => Double): Tensor[S] = (this, other) match
      case (Scalar(v1), Scalar(v2)) => Scalar(combine(v1, v2))
      case (@:(cs1), @:(cs2))       => @:(cs1.zipWith(cs2, _.elementwiseCombine(_, combine)))

  /** Tensor-scalar product */
  def *(scalar: Double): Tensor[S] = this.elementwiseMap(_ * scalar)

  /** Elementwise tensor sum */
  def +(other: Tensor[S]): Tensor[S] = this.elementwiseCombine(other, _ + _)

  /** Elementwise tensor product */
  def *(other: Tensor[S]): Tensor[S] = this.elementwiseCombine(other, _ * _)


object Tensor :
  def concat[Dim1 <: Nat, Dim2 <: Nat, TailShape <: TensorShape](a: @:[Dim1, TailShape], b: @:[Dim2, TailShape]): @:[Dim1 + Dim2, TailShape] = 
    @:(a.components.concat(b.components))

  // def concat[S1 <: TensorShape, S2 <: TensorShape](t1: Tensor[S1], t2: Tensor[S2]): Tensor[TensorShape.++[S1, S2]] = (t1, t2) match
  //   case (@:(cs1), @:(cs2)) => @:(cs1.concat(cs2))