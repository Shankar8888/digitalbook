import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-booksbyauthor',
  templateUrl: './booksbyauthor.component.html',
  styleUrls: ['./booksbyauthor.component.css']
})
export class BooksbyauthorComponent implements OnInit {


  blockMsg?:string;
  unBlockMsg?:string;
  blockErrMessage?:string;
  unBlockErrMessage?:string;
  isSearchFailed=false;
  isBlockFailed=false;
  isUnBlockFailed=false;
  isDeleteFailed=false;
  deleteMsg?:string;
  userId?: any;
  errorMessage="";
  booksList?:BookObject[];
  content?: string;

  constructor(private userService: UserService,private tokenStorage: TokenStorageService) { }


  ngOnInit(): void {
    const user = this.tokenStorage.getUser();
    this.userId = user.id;
    console.log("Fetching created book list");
    this.userService.booksByAuthor(this.userId).subscribe(
      data => {
        console.log(data);
        this.isSearchFailed=false;
        this.booksList=JSON.parse(data).bookList;
      },
      err => {
        this.isSearchFailed=true;
        this.errorMessage = JSON.parse(err.error).message;
      }
    );
  }

public blockBook(_bookId:number){
  this.userService.blockBook(_bookId).subscribe(
    data => {
      console.log(data);
      this.isBlockFailed=false;
      this.blockMsg=JSON.parse(data).message;
    },
    err => {
      this.isBlockFailed=true;
      this.blockErrMessage = JSON.parse(err.error).message;
    }
  );
}

public unBlockBook(_bookId:number){
  this.userService.unBlockBook(_bookId).subscribe(
    data => {
      console.log(data);
      this.isUnBlockFailed=false;
      this.unBlockMsg=JSON.parse(data).message;
    },
    err => {
      this.isUnBlockFailed=true;
      this.unBlockErrMessage = JSON.parse(err.error).message;
    }
  );
}

public deleteBook(_bookId:number){
  this.userService.deleteBook(_bookId).subscribe(
    data => {
      console.log(data);
      this.isDeleteFailed=false;
      this.unBlockMsg=JSON.parse(data).message;
    },
    err => {
      this.isDeleteFailed=true;
      this.unBlockErrMessage = JSON.parse(err.error).message;
    }
  );
}

}

export interface BookObject{
  id:number;
  category:string;
  title:string;
  author:string;
  price:number;
  publisher:string;
  publishedDate:Date;
  isBlocked:boolean;
}
