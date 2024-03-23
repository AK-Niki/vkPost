import org.junit.*
import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun add_PostIdZero() {
        val post1 = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )

        WallService.add(post1)

        assertEquals(1, post1.id)
    }

    @Test
    fun update_Existing() {
        val post1 = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )

        WallService.add(post1)

        val updatedPost = post1.copy(text = "Нереально крутой пост!")
        val result = WallService.update(updatedPost)

        assertTrue(result)
    }

    @Test
    fun update_NonExisting() {
        val updatedPost = Post(
            id = 999,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )

        val result = WallService.update(updatedPost)

        assertFalse(result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment = Comment(
            id = 1,
            postId = 100,
            fromId = 123,
            text = "Test comment"
        )
        WallService.createComment(comment.postId, comment)
    }

    @Test
    fun shouldAddCommentToExistingPost() {
        val post = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )
        WallService.add(post)

        val comment = Comment(
            id = 1,
            postId = 1,
            fromId = 789,
            text = "Комментарий к посту"
        )

        val addedComment = WallService.createComment(comment.postId ?: 0, comment)

        assertEquals(comment, addedComment)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrowWhenPostNotFound() {
        val wallService = WallService

        val comment = Comment(
            id = 0,
            postId = 100,
            fromId = 123,
            text = "Test comment"
        )
        wallService.createComment(comment.postId, comment)
    }

    @Test(expected = AssertionError::class)
    fun shouldThrowWhenCommentIsOffensive() {
        // Добавляем пост
        val post = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост для теста"
        )
        val addedPost = WallService.add(post)

        val comment = Comment(
            id = 1,
            postId = 1,
            fromId = 789,
            text = "дурак"
        )
        WallService.createComment(comment.postId, comment)

        val ownerId = 123
        val commentId = 1
        val reason = 0

        WallService.reportComment(ownerId, commentId, reason)
    }

}


