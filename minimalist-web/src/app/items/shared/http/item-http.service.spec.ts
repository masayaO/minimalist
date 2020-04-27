import {TestBed} from '@angular/core/testing';

import {ItemHttpService} from './item-http.service';
import {HttpClient} from "@angular/common/http";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpErrorHandler} from "../../../shared/http-error-handler.service";
import {MessageService} from "../../../shared/message.service";

describe('ItemHttpService', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;
  let itemHttpService: ItemHttpService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        HttpErrorHandler,
        MessageService
      ]
    });
  });


  it('should be created', () => {
    const service: ItemHttpService = TestBed.get(ItemHttpService);
    expect(service).toBeTruthy();
  });
});
