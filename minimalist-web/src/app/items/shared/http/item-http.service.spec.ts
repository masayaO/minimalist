import {TestBed} from '@angular/core/testing';

import {ItemHttpService} from './item-http.service';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpErrorHandler} from "../../../shared/http-error-handler.service";
import {MessageService} from "../../../shared/message.service";
import {ItemInfo} from "../models/itemInfo";

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
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    itemHttpService = TestBed.inject(ItemHttpService)
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    const service: ItemHttpService = TestBed.inject(ItemHttpService);
    expect(service).toBeTruthy();
  });

  describe('createItem', () => {
    it('return {}', () => {
      const itemInfo: ItemInfo = {
        minimalistId: 1,
        itemName: 'iPhone',
        itemComment: 'My Smart Phone',
        itemQuantity: 2,
        itemStatus: 'HAVING',
      };

      itemHttpService.createItem(itemInfo).subscribe(
        item => expect(item).toEqual({}),
        fail
      );

      const req = httpTestingController.expectOne(itemHttpService.baseUrl);
      const expectedResponse = new HttpResponse(
        { status: 200, statusText: 'OK'});

      req.event(expectedResponse);
      expect(req.request.method).toEqual('POST');
    });
  });
});
