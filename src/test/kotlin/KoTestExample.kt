import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

@DisplayName("메인 테스트")
class KoTestExample : DescribeSpec({
    describe("샘플 테스트") {
        val s = 1

        context("성공 검증") {
            it("s는 1이다") {
                s shouldBe 1
            }
            it("s는 정수이다") {
                s::class shouldBe Int::class
            }
        }

        context("실패 검증") {
            it ("s는 -1이 아니다") {
                s shouldNotBe -1
            }
            it("s는 문자열이 아니다") {
                s::class shouldNotBe String::class
            }
        }
    }
})
