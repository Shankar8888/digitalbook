import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-searchbook',
  templateUrl: './searchbook.component.html',
  styleUrls: ['./searchbook.component.css']
})
export class SearchbookComponent implements OnInit {

  constructor(private userService: UserService) { }

 content?:bookFound[];

  isSuccess=false;

  ngOnInit(): void {
  }

  search={
    title:"",
    author:"",
    publisher:"",
    category:"",
    price:""
  }

  
  public searchBook(){
    this.userService.getBooks(this.search).subscribe(
      data => {
        this.content = JSON.parse(data).bookResponseList;
        this.isSuccess=true;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

}


export interface bookFound{
  id:number;
  title:string;
  author:string;
  publisher:string;
  category:string;
  price:DoubleRange;
  logo:string;
}
