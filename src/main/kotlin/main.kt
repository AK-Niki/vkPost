fun main() {
    val post1 = Post(
        id = 1,
        ownerId = 123,
        fromId = 456,
        date = 18022024,
        text = "Пост намбер уан!"
    )

    val post2 = Post(
        id = 2,
        ownerId = 789,
        fromId = 101112,
        date = 20022024,
        text = "Это второй пост!"
    )

    WallService.add(post1)
    WallService.add(post2)

    println("Список постов:")
    for (post in WallService.posts) {
        println(post)
    }

    println("\nОбновление поста:")
    val updatedPost = post1.copy(text = "Нереально крутой пост!")
    WallService.update(updatedPost)
    println("Обновленный пост: $updatedPost")
}

// Класс для хранения информации о комментариях к посту
data class Comments(
    val count: Int = 0, // количество комментариев
    val canPost: Boolean = false, // информация о том, может ли текущий пользователь комментировать запись
)

// Класс для хранения информации о лайках к посту
data class Likes(
    val count: Int = 0, // количество пользователей, которым понравилась запись
    val userLikes: Boolean = false, // наличие отметки «Мне нравится» от текущего пользователя
)

// Класс для хранения информации о репостах записи
data class Reposts(
    val count: Int = 0, // количество пользователей, скопировавших запись
)

// Класс для хранения информации о просмотрах записи
data class Views(
    val count: Int = 0 // количество просмотров записи
)

// Класс для хранения информации о посте
data class Post(
    val id: Int, // идентификатор записи
    val ownerId: Int, // идентификатор владельца стены, на которой размещена запись
    val fromId: Int, // идентификатор автора записи
    val date: Int, // дата публикации записи
    val text: String, // текст записи
    val comments: Comments = Comments(), // информация о комментариях к записи
    val likes: Likes = Likes(), // информация о лайках к записи
    val reposts: Reposts = Reposts(), // информация о репостах записи
    val views: Views = Views(), // информация о просмотрах записи
    val canDelete: Boolean = false, // информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean = false, // информация о том, может ли текущий пользователь редактировать запись
)

object WallService {
    val posts = ArrayList<Post>()

    private var currentId = 0

    fun add(post: Post): Post {
        currentId++
        val newPost = post.copy(id = currentId)
        posts.add(newPost)
        return newPost
    }

    fun update(post: Post): Boolean {
        for (i in posts.indices) {
            if (posts[i].id == post.id) {
                posts[i] = post
                return true
            }
        }
        return false
    }
    fun clear() {
        posts.clear()
        currentId = 0
    }
}

