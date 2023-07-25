package com.github.lpld.tensorops
import Nat.*

enum TensorShape[R <: Nat, L <: Nat] :
    case Scalar() extends TensorShape[_0, _1]
    case Dim[Length <: Nat, PrevRank <: Nat, PrevShape <: TensorShape[PrevRank, ?]]() extends TensorShape[Succ[PrevRank], Length]

    type Rank = R
    type Length = L

object TensorShape :
    type Vector[N <: Nat] = TensorShape.Dim[N, _0, TensorShape.Scalar]
    type Matrix[M <: Nat, N <: Nat] = TensorShape.Dim[M, _1, Vector[N]]

enum Tensor[S <: TensorShape[?, ?]] :
    case Scalar(value: Double) extends Tensor[TensorShape.Scalar]
    case Dim[Length <: Nat, PrevRank <: Nat, PrevShape <: TensorShape[PrevRank, ?]](components: LList[Tensor[PrevShape], Length]) extends Tensor[TensorShape.Dim[Length, PrevRank, PrevShape]]

    type Shape = S

    def elementwiseMap(f: Double => Double): Tensor[S] = this match
        case Tensor.Scalar(v) => Tensor.Scalar(f(v))
        case Tensor.Dim(cs)   => Tensor.Dim(cs.map(_.elementwiseMap(f)))

    def elementwiseCombine(other: Tensor[S], combine: (Double, Double) => Double): Tensor[S] = (this, other) match
        case (Tensor.Scalar(v1), Tensor.Scalar(v2)) => Tensor.Scalar(combine(v1, v2))
        case (Tensor.Dim(cs1), Tensor.Dim(cs2)) => Tensor.Dim(cs1.zipWith(cs2, _.elementwiseCombine(_, combine)))

    /** Tensor-scalar product */
    def *(scalar: Double): Tensor[S] = this.elementwiseMap(_ * scalar)

    /** Elementwise tensor sum */
    def +(other: Tensor[S]): Tensor[S] = this.elementwiseCombine(other, _ + _)

    /** Elementwise tensor product */
    def *(other: Tensor[S]): Tensor[S] = this.elementwiseCombine(other, _ * _)

    def ++[PrevRank <: Nat, PrevShape <: TensorShape[PrevRank, ?], Length1 <: Nat, Length2 <: Nat]
    (other: Tensor.Dim[Length1, PrevRank, PrevShape])
    (using s: this.type =:= Tensor.Dim[Length2, PrevRank, PrevShape]) = ???

object Tensor :
    type Vector[N <: Nat] = Tensor.Dim[N, _0, TensorShape.Scalar]
    type Matrix[M <: Nat, N <: Nat] = Tensor.Dim[M, _1, TensorShape.Vector[N]]
