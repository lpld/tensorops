package com.github.lpld
package tensorops

import org.scalacheck.Prop.forAll

final class ExampleSuite extends TestSuite:
  test("hello world"):
    forAll: (int: Int, string: String) =>
      expectEquals(int, int)
      expectEquals(string, string)
