package ch.wisv.choice.service

import ch.wisv.choice.ApplicationTest
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(ApplicationTest::class))
@ActiveProfiles("test")
@DataJpaTest
abstract class ServiceTest(val testEntityManager: TestEntityManager) {

}
