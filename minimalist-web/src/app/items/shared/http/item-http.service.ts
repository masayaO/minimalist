import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ItemInfo} from "../models/itemInfo";
import {Observable} from "rxjs";
import {HandleError, HttpErrorHandler} from "../../../shared/http-error-handler.service";
import {catchError} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class ItemHttpService {

  baseUrl = 'http://localhost:9000/item/create';
  private readonly handleError: HandleError;

  constructor(
    private http: HttpClient,
    httpErrorHandler: HttpErrorHandler
  ) {
    this.handleError = httpErrorHandler.createHandleError('ItemHttpService');
  }

  createItem(itemInfo: ItemInfo): Observable<{}> {
    return this.http.post<{}>(this.baseUrl, itemInfo, httpOptions)
      .pipe(
        catchError(this.handleError(`createItem itemInfo: ${itemInfo}`, {}))
      )
  }
}
