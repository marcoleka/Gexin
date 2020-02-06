import { ITimeZone } from 'app/shared/model//time-zone.model';

export interface IAccesso {
    id?: number;
    gruppoId?: number;
    timeZones?: ITimeZone[];
    dipendenteId?: number;
}

export class Accesso implements IAccesso {
    constructor(public id?: number, public gruppoId?: number, public timeZones?: ITimeZone[], public dipendenteId?: number) {}
}
