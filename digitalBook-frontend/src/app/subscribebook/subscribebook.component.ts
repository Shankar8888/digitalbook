import { Component, OnInit } from '@angular/core';
import { bookFound } from '../searchbook/searchbook.component';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { bookContent } from '../subscribedbooks/subscribedbooks.component';

@Component({
  selector: 'app-subscribebook',
  templateUrl: './subscribebook.component.html',
  styleUrls: ['./subscribebook.component.css']
})
export class SubscribebookComponent implements OnInit {

  constructor(private userService: UserService,private tokenStorage: TokenStorageService) { }

  books?:bookFound[];
  isSuccessful = false;
  isSubscrFailed=false;
  userId?: any;
  errorMessage?:string ;
  content?:bookContent;

  ngOnInit(): void {
  }

  public getAllBooks(){
    // const user=this.tokenStorage.getUser();
    // this.userId=user.id;
    // console.log(user);
    this.userService.getAllbook().subscribe(
      data => {
        console.log(data);
        this.books = JSON.parse(data).bookResponseList;
        this.isSuccessful=true;
        this.isSubscrFailed=false;
      },
      err => {
        console.log(err.error.message);
        // this.content = err.error.message;
        this.errorMessage = JSON.parse(err.error).message;
        this.isSubscrFailed=true;

  
      }
    );
  }

   public subscribeBook(_bookId: number){
    const user=this.tokenStorage.getUser();
    this.userId=user.id;
    console.log(user);
    this.userService.subscribeBook(this.userId,_bookId).subscribe(
      data => {
        console.log(data);
        this.content = JSON.parse(data).message;
        this.isSuccessful=true;
       this.isSubscrFailed=false;
      },
      err => {
        console.log(err.error.message);
        // this.content = err.error.message;
        this.errorMessage = JSON.parse(err.error).message;
        this.isSubscrFailed=true;
      }
    );
  }
}
