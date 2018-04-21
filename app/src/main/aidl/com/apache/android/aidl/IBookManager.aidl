// IBookManager.aidl
package com.apache.android.aidl;
import com.apache.android.aidl.Book;

// Declare any non-default types here with import statements

interface IBookManager {

   List<Book> getBookList();

   void addBook(in Book book);

}
