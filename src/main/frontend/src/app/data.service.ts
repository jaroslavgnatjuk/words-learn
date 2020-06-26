import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

  getData(): Observable<Array<Array<string>>> {
    return this.http.get<Array<Array<string>>>('api/data');
  }

  setData(range: string, data: string): Observable<void> {
    return this.http.put<void>(`api/data?range=${range}&data=${data}`, {});
  }
}
