package com.github.caay2000.projectskeleton.context.loan.secondaryadapter.database

import arrow.core.Either
import com.github.caay2000.common.database.RepositoryError
import com.github.caay2000.memorydb.InMemoryDatasource
import com.github.caay2000.projectskeleton.context.loan.application.BookRepository
import com.github.caay2000.projectskeleton.context.loan.domain.Book
import com.github.caay2000.projectskeleton.context.loan.domain.BookId
import com.github.caay2000.projectskeleton.context.loan.domain.BookIsbn

class InMemoryBookRepository(private val datasource: InMemoryDatasource) : BookRepository {

    companion object {
        private const val TABLE_NAME = "loan.book"
    }

    override fun save(book: Book): Either<RepositoryError, Unit> =
        Either.catch { datasource.save(TABLE_NAME, book.id.toString(), book) }
            .mapLeft { RepositoryError.Unknown(it) }
            .map { }

    override fun findById(id: BookId): Either<RepositoryError, Book> =
        Either.catch { datasource.getById<Book>(TABLE_NAME, id.toString())!! }
            .mapLeft { error ->
                when (error) {
                    is NullPointerException -> RepositoryError.NotFoundError()
                    is NoSuchElementException -> RepositoryError.NotFoundError()
                    else -> RepositoryError.Unknown(error)
                }
            }

    override fun searchByIsbn(isbn: BookIsbn): Either<RepositoryError, List<Book>> =
        Either.catch { datasource.getAll<Book>(TABLE_NAME).filter { it.isbn == isbn } }
            .mapLeft { error -> RepositoryError.Unknown(error) }
}
