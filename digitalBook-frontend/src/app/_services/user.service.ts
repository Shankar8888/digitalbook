import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8081/digitalbooks/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' ,responseType: 'text'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {



  constructor(private http: HttpClient) { }

  // getPublicContent(): Observable<any> {
  //   return this.http.get(API_URL + 'all', { responseType: 'text' });
  // }

  getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.http.get(API_URL + 'mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }

  getBooks(search: any) {

    let queryParams = new HttpParams();
    queryParams = queryParams.append("title",search.title)
    .append("author",search.author).append("publisher",search.publisher)
    .append("category",search.category).append("price",search.price);
 
    return this.http.get(API_URL + 'search',{params:queryParams, responseType: 'text'});
  }

  getCreatedBooks(title:string,
    author:string,
    publisher:string,
    category:string,
    price:DoubleRange,
    content:string,
    publishedDate:Date,logo:string){
    return this.http.post(API_URL + 'saveBooks', {
      title,
      author,
      publisher,
      category,
      price,
      content,
      publishedDate,logo}, httpOptions);
  }

  getSubscriptions(id: number) {
    return this.http.get(API_URL + 'reader/'+id+'/books',{responseType: 'text'});
    console.log("subscription done");
  }

  cancelSubscription(_userId: any ,_subscriptionId: any) {
    return this.http.get(API_URL + 'reader/'+_userId+'/books/'+_subscriptionId+'/cancel',{responseType: 'text'});
  }

  readBookContent(_userId: any, _subscriptionId: any) {
    return this.http.get(API_URL + 'reader/'+_userId+'/books/'+_subscriptionId+'/read',{responseType: 'text'});
  }

  getAllbook() {
    return this.http.get(API_URL,{responseType: 'text'});
  }

  subscribeBook(userId:number, bookId:number) {
    return this.http.post(API_URL + 'reader/'+userId+'/'+bookId, "{}", {
   responseType: 'text'
    });
  }
}
